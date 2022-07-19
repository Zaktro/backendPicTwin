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

package cl.ucn.disc.dsm.pictwin.backend.services;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;

/**
 * The PicTwins Operations.
 *
 * @author Matias Canales Benavides.
 */


public interface PicTwin {

    /**
     * Creates a User with a specific password.
     *
     * @param user to create
     * @param password to hash.
     * @return the User created.
     */
    User create (User user, String password);

    /**
     * Return the User with the email and password.
     *
     * @param email	to search.
     * @param password to use.
     * @return the User.
     */
	User authenticate(String email, String password);

    /**
     * Create a Twin using the Pic from the User.
     *
     * @param pic	to use.
     * @param idUser who create the Pic to use.
     * @return the Twin created.
     */
    Twin createTwin(Pic pic, Long idUser);
    /**
     * Dislike a Pic in a Twin.
     *
     * @param idTwin to dislike.
     * @param idUser who dislike the Twin.
     */
    void dislike(Long idTwin, Long idUser);
    /**
     * @return the number of users in the system.
     */
    Long getUserSize();

}

