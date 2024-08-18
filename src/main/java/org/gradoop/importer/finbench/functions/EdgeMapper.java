/*
 * Copyright © 2014 - 2024 Leipzig University (Database Research Group)
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
package org.gradoop.importer.finbench.functions;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple8;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.model.impl.properties.Properties;
import org.gradoop.temporal.model.impl.pojo.TemporalEdge;
import org.gradoop.temporal.model.impl.pojo.TemporalEdgeFactory;

import java.io.Serializable;
import java.text.ParseException;

import static org.gradoop.importer.finbench.functions.HelperFunction.convertTimeToUnix;

/**
 * The EdgeMapper class is responsible for mapping tuples to TemporalEdge objects for various types of edges.
 */

public class EdgeMapper implements Serializable {

    public static final String SOURCE_ID = "SourceID";
    public static final String TARGET_ID = "TargetID";
    private TemporalEdgeFactory factory;

    public EdgeMapper(TemporalEdgeFactory factory){
        this.factory = factory;
    }

    /**
     * Maps a tuple representing a transfer to a TemporalEdge.
     *
     * @param data the tuple containing transfer data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */


    public TemporalEdge mapTransfer(Tuple2<Tuple2<Tuple8<String, String, Double, String, String, String, String, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple8<String, String, Double, String, String, String, String, String> edgeData = data.f0.f0;

        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;
        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("amount", edgeData.f2);
        edgeProps.set("timestamp", edgeData.f3);
        edgeProps.set("orderNumber", edgeData.f4);
        edgeProps.set("comment", edgeData.f5);
        edgeProps.set("payType", edgeData.f6);
        edgeProps.set("goodsType", edgeData.f7);

        TemporalEdge edge = factory.createEdge("Transfer", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f3));

        return edge;
    }

    /**
     * Maps a tuple representing an investment to a TemporalEdge.
     *
     * @param data the tuple containing investment data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapInvest(Tuple2<Tuple2<Tuple4<String, String, Double, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, Double, String> edgeData = data.f0.f0;

        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;
        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("ratio", edgeData.f2);
        edgeProps.set("timestamp", edgeData.f3);


        TemporalEdge edge = factory.createEdge("Invest", sourceId, targetId, edgeProps);
        edge.setValidFrom(convertTimeToUnix(edgeData.f3));

        return edge;
    }

    /**
     * Maps a tuple representing an ownership to a TemporalEdge.
     *
     * @param data the tuple containing ownership data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapOwn(Tuple2<Tuple2<Tuple3<String, String, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple3<String, String, String> edgeData = data.f0.f0;

        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;
        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("timestamp", edgeData.f2);

        TemporalEdge edge = factory.createEdge("Own", sourceId, targetId, edgeProps);
        edge.setValidFrom(convertTimeToUnix(edgeData.f2));

        return edge;
    }

    /**
     * Maps a tuple representing a deposit to a TemporalEdge.
     *
     * @param data the tuple containing deposit data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapDeposit(Tuple2<Tuple2<Tuple4<String, String, Double, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, Double, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("amount", edgeData.f2);
        edgeProps.set("timestamp", edgeData.f3);

        TemporalEdge edge = factory.createEdge("Deposit", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f3));

        return edge;
    }

    /**
     * Maps a tuple representing a repayment to a TemporalEdge.
     *
     * @param data the tuple containing repayment data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapRepay(Tuple2<Tuple2<Tuple4<String, String, Double, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, Double, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("amount", edgeData.f2);
        edgeProps.set("timestamp", edgeData.f3);

        TemporalEdge edge = factory.createEdge("Repay", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f3));

        return edge;
    }

    /**
     * Maps a tuple representing a sign in to a TemporalEdge.
     *
     * @param data the tuple containing sign in data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapSignIn(Tuple2<Tuple2<Tuple4<String, String, String, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, String, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("timestamp", edgeData.f2);
        edgeProps.set("location", edgeData.f3);

        TemporalEdge edge = factory.createEdge("SignIn", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f2));

        return edge;
    }

    /**
     * Maps a tuple representing a withdrawal to a TemporalEdge.
     *
     * @param data the tuple containing withdrawal data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapWithdraw(Tuple2<Tuple2<Tuple4<String, String, Double, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, Double, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("amount", edgeData.f2);
        edgeProps.set("timestamp", edgeData.f3);

        TemporalEdge edge = factory.createEdge("Withdraw", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f3));

        return edge;
    }

    /**
     * Maps a tuple representing a guarantee to a TemporalEdge.
     *
     * @param data the tuple containing guarantee data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapGuarantee(Tuple2<Tuple2<Tuple4<String, String, String, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, String, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set("TargetID", edgeData.f1);
        edgeProps.set("timestamp", edgeData.f2);
        edgeProps.set("relationship", edgeData.f3);

        TemporalEdge edge = factory.createEdge("Guarantee", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f2));

        return edge;
    }

    /**
     * Maps a tuple representing an application to a TemporalEdge.
     *
     * @param data the tuple containing application data
     * @return the created TemporalEdge for the transfer
     * @throws ParseException if an error occurs during converting DateTime to UNIX
     */

    public TemporalEdge mapApply(Tuple2<Tuple2<Tuple4<String, String, String, String>, GradoopId>, GradoopId> data) throws ParseException {

        Tuple4<String, String, String, String> edgeData = data.f0.f0;
        GradoopId sourceId = data.f0.f1;
        GradoopId targetId = data.f1;

        Properties edgeProps = Properties.create();

        edgeProps.set(SOURCE_ID, edgeData.f0);
        edgeProps.set(TARGET_ID, edgeData.f1);
        edgeProps.set("timestamp", edgeData.f2);
        edgeProps.set("organization", edgeData.f3);

        TemporalEdge edge = factory.createEdge("Apply", sourceId, targetId, edgeProps);

        edge.setValidFrom(convertTimeToUnix(edgeData.f2));

        return edge;
    }
}
