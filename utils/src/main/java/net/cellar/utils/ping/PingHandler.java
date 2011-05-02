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

package net.cellar.utils.ping;

import net.cellar.core.command.CommandHandler;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;

/**
 * @author iocanel
 */
public class PingHandler extends CommandHandler<Ping, Pong> {

    public static final String SWITCH_ID = "net.cellar.command.ping.switch";

    private final Switch commandSwitch = new BasicSwitch(SWITCH_ID);

    @Override
    public Pong execute(Ping command) {
        return new Pong(command.getId());
    }

    @Override
    public Class<Ping> getType() {
        return Ping.class;
    }

    @Override
    public Switch getSwitch() {
        return commandSwitch;
    }
}
