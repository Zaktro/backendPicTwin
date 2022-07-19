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

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * The picture image.
 *
 * @author Matias Canales Benavides.
 */
@Entity
@Table(name = "pics")
@Builder // Replace the constructor
@NoArgsConstructor
@AllArgsConstructor
public final class Pic {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The instant when the Pic was saved.
     */
    @Getter
    @Builder.Default
    private ZonedDateTime timestamp = ZonedDateTime.now();

    /**
     * The amount of dislikes that a pic has at the time.
     */
    @Getter
    @Builder.Default
    private Integer dislikes = 0;

    /**
     * The corresponding latitude of the user's location.
     */
    @Getter
    private Double latitude;

    /**
     * The corresponding longitude of the user's location.
     */
    @Getter
    private Double longitude;

    /**
     * The corresponding error regarding the user localization's coordinates.
     */
    @Getter
    private Double error;

    /**
     * The amount of views a pic has accumulated over the time.
     */
    @Getter
    @Builder.Default
    private Integer views = 0;

    /**
     * A corresponding title to the pic.
     */
    @Getter
    private String name;

    /**
     * The photo associated to the pic.
     */
    @Getter
    private byte[] picture;

    /**
     * The Owner.
     */
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JsonBackReference
    private User owner;

    /**
     * Increment in one the dislikes.
     *
     * @return the dislikes number.
     */
    public Integer incrementDislikes() {
        this.dislikes++;
        return this.dislikes;
    }

    /**
     * Increment in one the views.
     *
     * @return the views number.l
     */
    public Integer incrementViews() {
        this.views++;
        return this.views;
    }


}


