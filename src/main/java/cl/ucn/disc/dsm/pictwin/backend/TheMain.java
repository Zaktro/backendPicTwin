/*
 * Copyright 2022 Matias Canales Benavides.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.ucn.disc.dsm.pictwin.backend;

import cl.ucn.disc.dsm.pictwin.backend.mcb.DatabaseLoader;
import cl.ucn.disc.dsm.pictwin.backend.mcb.UserRepository;
import cl.ucn.disc.dsm.pictwin.backend.mcb.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backend.mcb.PicRepository;

import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import lombok.extern.slf4j.Slf4j;


/**
 * TODO: Add Javadoc
 */
@Slf4j
public final class TheMain {

    private final static DatabaseLoadre databaseLoader = new DatabaseLoader(userRepository, twinRepository, picRepository);

    /**
     * The Main.
     * @param args to use
     */
    public static void main(String[] args) throws Exception{
        databaseLoader.run();
        log.debug(String.valueOf(databaseLoader));
    }
}

