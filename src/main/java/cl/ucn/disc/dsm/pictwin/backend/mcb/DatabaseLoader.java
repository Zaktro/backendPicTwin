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

package cl.ucn.disc.dsm.pictwin.backend.mcb;

import cl.ucn.disc.dsm.pictwin.backend.Utils;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/**
 * The Database Loader.
 */
@Slf4j
@Component
public class DatabaseLoader implements CommandLineRunner {
    /**
     * The PicTwin implementation
     */
    private final PicTwin picTwin;

    /**
    * The Constructor.
 	*
 	* @param picTwin to use.
 	*/
    public DatabaseLoader(@Autowired PicTwin picTwin) {
        this.picTwin = picTwin;
    }

    /**
 	* Callback used to run the bean.
 	*
 	* @param args incoming main method arguments
 	* @throws Exception on error
 	*/
    @Override
	public void run(String... args) throws Exception {
        	if (this.picTwin.getUserSize() != 0) {
            	log.info("Database already seeded, skipping!");
            	return;
			}

        	log.warn("No data found in database, seeding the database ..");
    		// The main user


			User user = User.builder()
					.email("admin@ucn.cl")
        			.password("admin123")
        			.strikes(0)
        			.build();
			Utils.printObject("User to create:", user);

			// Storage the User
			this.picTwin.create(user, "admin123");
			Utils.printObject("User created:", user);

			// Create the first Twin
			Twin twin1 = this.picTwin.createTwin(Pic.builder()
					.name("The first Pic: UCN")
					.latitude(-23.6803026)
					.longitude(-70.4121427)
					.error(3.5)
					.owner(user)
					.build(), user.getId());
			Utils.printObject("Twin1 created:", twin1);

			// Create the second Twin
			Twin twin2 = this.picTwin.createTwin(Pic.builder()
					.name("The second Pic: Parque de los Eventos")
					.latitude(-23.6281221)
					.longitude(-70.3952909)
					.error(5.7)
					.owner(user)
					.build(), user.getId());
			Utils.printObject("Twin2 created:", twin2);

			// Create the third Twin
			Twin twin3 = this.picTwin.createTwin(Pic.builder()
					.name("The third Pic: Quebrada Carrizo")
					.latitude(-23.6977891)
					.longitude(-70.4105903)
					.error(5.7)
					.owner(user)
					.build(), user.getId());
			Utils.printObject("Twin3 created", twin3);

			// Create the fourth Twin
			Twin twin4 = this.picTwin.createTwin(Pic.builder()
					.name("The fourth Pic: Balneario Juan Lopez")
					.latitude(-23.5114433)
					.longitude(-70.5218646)
					.error(0.3)
					.owner(user)
					.build(), user.getId());
			Utils.printObject("Twin4", twin4);

			log.info("Database Data Loader: Done.");
			}

}


