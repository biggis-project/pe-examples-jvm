/*
 * Copyright 2017 FZI Forschungszentrum Informatik
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

package org.streampipes.examples.jvm.sink.dashboard;

import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.model.DataSinkType;
import org.streampipes.model.graph.DataSinkDescription;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.sdk.builder.DataSinkBuilder;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.standalone.ConfiguredEventSink;
import org.streampipes.wrapper.standalone.declarer.StandaloneEventSinkDeclarer;

public class DashboardController extends StandaloneEventSinkDeclarer<DashboardParameters> {

    @Override
    public DataSinkDescription declareModel() {
        return DataSinkBuilder.create("dashboard_sink", "Dashboard Sink", "This sink will be used to visualize data" +
                " " +
                "streams in the StreamPipes dashboard")
                .category(DataSinkType.VISUALIZATION_CHART)
                .requiredPropertyStream1(EpRequirements.anyProperty())
                .iconUrl(PeJvmConfig.getIconUrl("dashboard-icon"))
                .supportedFormats(SupportedFormats.jsonFormat())
                .supportedProtocols(SupportedProtocols.kafka())
                .build();
    }

    @Override
    public ConfiguredEventSink<DashboardParameters> onInvocation(DataSinkInvocation invocationGraph) {
         return new ConfiguredEventSink<>(new DashboardParameters(invocationGraph), Dashboard::new);
    }

}
