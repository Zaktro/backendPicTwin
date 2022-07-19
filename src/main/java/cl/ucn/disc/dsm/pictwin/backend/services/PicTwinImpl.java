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

import cl.ucn.disc.dsm.pictwin.backend.mcb.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.mcb.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backend.mcb.UserRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
 * The {@Link PicTwin} implementation.
 * @author Matias Canales Benavides.
 */

@Slf4j
@Service
public class PicTwinImpl implements PicTwin {

    /**
     * The Hasher for the user's password (Argon2).
     */

    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    /**
     * The Random.
     */

    private final static Random RANDOM = new Random();
    /**
     * The Pic Repository.
     */

    private final PicRepository picRepository;
    /**
     * The Twin Repository
     */

    private final TwinRepository twinRepository;
    /**
     * The User Repository.
     */

    private final UserRepository userRepository;

    /**
     * Build the PicTwinImplementation.
     *
     * @param picRepository  to use.
     * @param twinRepository to use.
     * @param userRepository to use.
     */

    @Autowired
    public PicTwinImpl(PicRepository picRepository, TwinRepository twinRepository, UserRepository userRepository) {
        this.picRepository = picRepository;
        this.twinRepository = twinRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a User with a specific password.
     *
     * @param user     to create
     * @param password to hash.
     * @return the User created.
     */

    @Override
    @Transactional
    public User create(@NonNull User user, @NonNull String password) {

        // If the User already exists
        if (this.userRepository.findOneByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The User with email <" + user.getEmail() + "> it's already in the " + "system");
        }

        //Using the password encoder to hash
        String passwordHash = PASSWORD_ENCODER.encode(password);


        // Replace the password with the hash
        user.setPassword(passwordHash);

        // Save into the repository
        return this.userRepository.save(user);
    }

    /**
     * Return the User with the email and password.
     *
     * @param email    to search.
     * @param password to use.
     * @return the User.
     */
    @Override
    public User authenticate(@NonNull String email, @NonNull String password) {

// Find by Email
        Optional<User> userOptional = this.userRepository.findOneByEmail(email);
        log.debug("User: {}", userOptional);
        return userOptional.orElseThrow(() -> new RuntimeException("Wrong Credendials or User Not Found"));
    }

    /**
     * Create a Twin using the Pic from the User.
     *
     * @param pic    to use.
     * @param idUser who create the Pic to use.
     * @return the Twin created.
     */
    @Override
    @Transactional
    public Twin createTwin(@NonNull Pic pic, @NonNull Long idUser) {
// The user
        User owner = this.userRepository.findById(idUser).orElseThrow();

        log.debug("Pics: {} in User: {}", owner.getTwins().size(), owner.getEmail());

// Set the User
        pic.setOwner(owner);

// Store the Pic
        this.picRepository.save(pic);

// Get all the pics
        List<Pic> pics = this.picRepository.findAll();
        log.debug(" Number of Pics in the database: {}", pics.size());

// Remove my Owns Pics
        List<Pic> picsFiltered = pics.stream().filter(p -> !p.getOwner().getId().equals(idUser)).toList();
        if (picsFiltered.size() == 0) {
            log.warn("Re-using Pics from database.");
            picsFiltered = pics;
        }

//Select a random Pic
        //FIXME: Sort by views and select the least used
        Pic your = picsFiltered.size() == 0 ? pic: picsFiltered.get(RANDOM.nextInt(picsFiltered.size()));

        //Increment the views
        your.incrementViews();

        //Save the increment
        this.picRepository.save(your);

        //Store the Twin
        Twin twin = Twin.builder()
                .my(pic)
                .yours(your)
                .owner(owner)
                .build();

        //Save the Twin
        this.twinRepository.save(twin);

        //Add the Twin to the user
        owner.add(twin);
        this.userRepository.save(owner);

        return twin;
    }

    /**
     * Dislike a Pic in a twin
     * @param idTwin to dislike
     * @param idUser who dislike the Twin
     */
    @Override
    @Transactional
    public void dislike(@NonNull Long idTwin, @NonNull Long idUser){
        //Retrieve the Twin
        Optional<Twin> oTwin = this.twinRepository.findById(idTwin);

        //Check if exists
        Twin twin = oTwin.orElseThrow(() -> new RuntimeException("Can't find Twin with id: " + idTwin));

        //Check the owner of the Pic
        if(!idUser.equals(twin.getMy().getOwner().getId())){
            throw new RuntimeException("Twin id <" + idTwin + "> not owned by User id<" + idUser + ">!");

        }

        //Set the dislike and save
        twin.setDislike(true);
        this.twinRepository.save(twin);

        //Increment the dislike of twin and save
        Pic yours = twin.getYours();
        yours.incrementDislikes();
        this.picRepository.save(yours);

        //Increment the strikes in User and save
        User user = yours.getOwner();
        user.incrementStrikes();
        this.userRepository.save(user);

    }

    /**
     * @return the number of user in the system
     */
    @Override
    public Long getUserSize(){
        return this.userRepository.count();
    }
}