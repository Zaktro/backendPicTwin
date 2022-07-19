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
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  The Twin of Pic.
 *
 * @author Matias Canales Benavides.
 */

@Entity
@Table(name = "twins")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Twin {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
    * The dislike
    */
    @Getter
    @Setter
    @Builder.Default
    private Boolean dislike = Boolean.FALSE;

    /**
    * The Pic
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic my;

    /**
    * The Pic
    */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic yours;

    /**
     * The Owner
     */
    @ManyToOne(optional = false, fetch=FetchType.EAGER) @Getter
    @JsonBackReference
    private User owner;


}
