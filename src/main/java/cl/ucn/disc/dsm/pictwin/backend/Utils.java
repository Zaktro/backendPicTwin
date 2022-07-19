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

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * The Utils.
 *
 * @author Matias Canales Benavides.
 */

@Slf4j
public final class Utils {
    /**
     * Print in log the internal of one object.
     *
     * @param objectName to print.
     * @param o          to print.
     */
    public static void printObject(@NonNull String objectName, Object o) {
        if (o != null) {
            log.debug("{}: {}", objectName, ToStringBuilder.reflectionToString(o, RecursiveToStringStyle.MULTI_LINE_STYLE));
        } else {
            log.debug("{}: {}", objectName, null);
        }

    }


}