/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cellar.core;

import net.cellar.core.control.Switch;

import java.io.Serializable;

/**
 * Generic Producer Interface.
 *
 * @author iocanel
 */
public interface Producer<T extends Serializable> {

    /**
     * Produce an object.
     *
     * @param obj
     */
    public void produce(T obj);

    /**
     * Returns the {@code Switch}.
     *
     * @return
     */
    public Switch getSwitch();
}
