/*
 * Copyright (c) 2019 The StreamX Project
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.streamxhub.streamx.console.core.metrics.flink;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamxhub.streamx.common.util.HdfsUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author benjobs
 */
@Data
public class CheckPoints implements Serializable {

    private Counts counts;

    private List<CheckPoint> history;

    private Latest latest;

    private Object summary;

    @Data
    public static class CheckPoint implements Serializable {
        private Long id;
        private String status;

        @JsonProperty("external_path")
        private String externalPath;

        @JsonProperty("is_savepoint")
        private Boolean isSavepoint;

        @JsonProperty("latest_ack_timestamp")
        private Long latestAckTimestamp;

        @JsonProperty("checkpoint_type")
        private String checkpointType;

        @JsonProperty("trigger_timestamp")
        private Long triggerTimestamp;

        @JsonProperty("state_size")
        private Long stateSize;

        @JsonProperty("processed_data")
        private Long processedData;

        @JsonProperty("persisted_data")
        private Long persistedData;

        @JsonProperty("num_subtasks")
        private Integer numSubtasks;

        @JsonProperty("num_acknowledged_subtasks")
        private Integer numAcknowledgedSubtasks;

        @JsonProperty("end_to_end_duration")
        private Long endToEndDuration;

        private Boolean discarded;

        private Object tasks;

        @JsonProperty("alignment_buffered")
        private Long alignmentBuffered;

        public boolean isCompleted() {
            return "COMPLETED".equals(this.status);
        }

        public String getPath() {
            return this.externalPath.replaceFirst("^hdfs:",HdfsUtils.getDefaultFS());
        }
    }

    @Data
    public static class Counts implements Serializable {
        private Integer completed;
        private Integer failed;
        @JsonProperty("in_progress")
        private Integer inProgress;
        private Integer restored;
        private Integer total;
    }

    @Data
    public static class Latest implements Serializable {
        private CheckPoint completed;
        private Object failed;
        private Object restored;
        private Object savepoint;
    }
}
