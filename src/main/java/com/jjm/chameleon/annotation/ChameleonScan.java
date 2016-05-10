/*
 * Copyright 2016-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jjm.chameleon.annotation;

import java.lang.annotation.*;

/**
 * Indicates the components to be proceeded when the chameleon starts
 * Such classes are considered as the field to be used
 *
 * @author Jonathan Jara Morales
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ChameleonScan {

    /**
     * Identifies the base packages of the repository components
     * @return
     */
    String[] basePackages();
}
