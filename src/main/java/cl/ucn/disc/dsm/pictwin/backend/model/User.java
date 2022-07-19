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

package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The User of the App.
 *
 * @author Matias Canales Benavides.
 */
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {

    /**
     * The Id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The email used by the user.
     */
    @Getter
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * The password that the user inputs.
     */
    @Getter
    @Setter
    private String password;

    /**
     * The strike count of the user.
     */
    @Getter
    private Integer strikes;

    /**
     * The state of the user.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Getter
    @Setter
    private State state = State.ACTIVE;

    /**
     *  The Twins of the user.
     */

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    @JsonManagedReference
    private List<Twin> twins = new ArrayList<>();

    /**
     * The function increases the amount of strikes by one, whenever a pic is considered inappropriate.
     *
     * @return the number of strikes.l
     */
    public Integer incrementStrikes() {
        this.strikes++;
        return this.strikes;
    }

    /**
     * Insert a Twin in the list.
     *
     * @param twin to add.
     */
    public void add(final Twin twin){
        this.twins.add(twin);
    }

}


