![logo](https://github.com/JJaraM/Chameleon/blob/master/logo.png)

# What is Chameleon?
Is a java framework that helps the copy objects that have the same attributes types, without the necessity to create heavy factories and deal with the creation of the subrelations objects. 

# Problem to attack 

For example **Chameleon** will prevent this.

```java
public class CarFactory {

     public static CarFactory instance = null;

     public static CarFactory getInstance() {
          if (instance == null) {
               instance = new CarFactory();
          }
          return instance;
     }

     public CarDTO create(Car car) {
          Motor motor = MotorFactory.getInstance().create(car.getMotor()); // First factory created because we have a relation
          MotorDTO motorDTO = new MotorDTO(); // Object from relation
          motorDTO.setId(motor.getId());
          CarDTO carDTO = new CarDTO(); 
          carDTO.setModel(car.getModel()); 
          carDTO.setYear(car.getYear());
          carDTO.setMotor(motorDTO);
          return carDTO;
     }
}
```

Representation of MotorFactory class.

```java
public class MotorFactory {

     public static MotorFactory instance = null;

     public static MotorFactory getInstance() {
          if (instance == null) {
               instance = new MotorFactory();
          }
          return instance;
     }

     public MotorDTO create(Motor motor) {
          MotorDTO motorDTO = new MotorDTO();
          motorDTO.setId(motor.getId());
          return motorDTO;
     }
}
```

In the previous example we are creating a new CarDTO, maybe we want to return this value from an API and hide some fields, this scenario is the most common, we create a factory for each class, but this work can be boring and very difficult to give support, **why?**, imagine the next scenario; we want to display the all cars but we need to display only the model and we need to hide the year. Many people creates a new method in CarFactory and pass the object but ignore the line.

```java
public class CarFactory {

     public static CarFactory instance = null;

     public static CarFactory getInstance() {
          if (instance == null) {
               instance = new CarFactory();
          }
          return instance;
     }

     public CarDTO create(Car car) {
          Motor motor = MotorFactory.getInstance().create(car.getMotor()); // First factory created because we have a relation
          MotorDTO motorDTO = new MotorDTO(); // Object from relation
          motorDTO.setId(motor.getId());
          CarDTO carDTO = new CarDTO(); 
          carDTO.setModel(car.getModel()); 
          carDTO.setYear(car.getYear());
          carDTO.setMotor(motorDTO);
          return carDTO;
     }
     
       public CarDTO createWithoutYear(Car car) {
          car.setYear(null);
          return create(car);
     }
}
```

As you can see we are adding a little of complexity to our factory, because can be many of possiblities, and each posiblity means a new method.

# How **chameleon** will solved this problem?

As you saw in the last section we had the problem to create to many methods in a factory, the propose to chameleon is manage this creation using a similar sintax as SQL, we can use this query and select the fields that we want and ignore the all complexity of object creation.

```java
@Repository
public interface PlaceDTORepository {
     @Query("SELECT C.model, C.year, C.id, M.id FROM Car c JOIN Motor M")
     Set<PlaceDTO> fetchCollection(Set<Car> cars);
}
```

As you saw the only thing that we need is to create a simple interface and add the annotation @Repository, add out expected result and the object to convert, and what happend if we want to ignore car's years? It's easy we only need to remove the **C.year** column from the query

```java
@Repository
public interface CarDTORepository {
     @Query("SELECT C.model, C.year, C.id, M.id FROM Car c JOIN Motor M")
     Set<PlaceDTO> fetchCollection(Set<Car> cars);
    
     @Query("SELECT C.model, C.id, M.id FROM Car c JOIN Motor M")
     Set<PlaceDTO> fetchCollectionWithoutCarYear(Set<Car> cars);
}
```

And to call out repository we need to inject our class in the desire place.

```java
public class PlaceController {

     @Resource private CarDTORepository carDTORepository;
     
     public Set<PlaceDTO> getList() {
          Set<Car> cars = someMethodRetrieveAJPACollection(...);
          return carDTORepository.fetchCollection(cars);
     }
}
```

As you can see we are not working any more with factories, and the Chameleon framework deal with the object creations.

# Dependencies

* spring-aop 4.2.5.RELEASE
* spring-beans 4.2.4.RELEASE
* spring-context 4.2.4.RELEASE
* spring-aop 4.2.5.RELEASE
* aspectjweaver 1.8.8
* aspectjrt 1.8.8
* hibernate-core 5.1.0.Final
