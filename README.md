![logo](https://github.com/JJaraM/Chameleon/blob/master/logo.png)

# What is Chameleon?
Is a java framework that helps the conversion of POJO and JPA object to DTO Objects, without the necessity to create heavy factories and deal with the creation of the subrelations objects. 

For example Chameleon will prevent this.

```java
public class Factory {

     public static Factory instance = null;

     public static Factory getInstance() {
          if (instance == null) {
               instance = new Factory();
          }
          return instance;
     }

     public CarDTO create(Car car) {
          CarDTO carDTO = new CarDTO();          
          carDTO.setModel(car.getModel()); 
          Motor motor = car.getMotor(); //Problem when deal with relations 
          MotorDTO motorDTO = new MotorDTO();
          motorDTO.setId(motor.getId());          
          carDTO.setMotor(motorDTO);
     }

}
```

As you can see we are creating a factory and passing the attributes to a new object, sometimes this task can be big because what happen if we want convert an object to DTO with 10 relations, we need to create 10 factories classes and set the desire attributes, and when we can to reuse the factory this can be difficult because not always we want to display the same attributes.

With Chameleon the only thing that we need to do is to create a simple Repository class and define the attributes that we want to use as the next example:

Repository:

```java
@Repository
public interface PlaceDTORepository {
    @Query("SELECT V.referenceId, V.name, P.referenceId, P.prefix, P.suffix, P.width, P.height FROM Place V JOIN Photos P")
    Set<PlaceDTO> fetchNearPlacesByLocationName(Set<Place> source);
}
```

And in the place that we want to use we need to inject the instance:

```java
public class PlaceController {
     public Set<PlaceDTO> getList() {
          Set<Place> places = someMethodRetrieveAJPACollection(...);
          placeDTORepository.fetchNearPlacesByLocationName(places);
     }
}
```

As you can see we are not working any more with factories, and the Chameleon framework deal with the heavy relations and Lazy JPA collections.

# Dependencies

* spring-aop 4.2.5.RELEASE
* spring-beans 4.2.4.RELEASE
* spring-context 4.2.4.RELEASE
* spring-aop 4.2.5.RELEASE
* aspectjweaver 1.8.8
* aspectjrt 1.8.8
* hibernate-core 5.1.0.Final
