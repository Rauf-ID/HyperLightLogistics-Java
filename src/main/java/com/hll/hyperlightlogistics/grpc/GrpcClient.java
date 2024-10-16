/*
 * This file is part of HyperLightLogistics-Java.
 *
 * HyperLightLogistics-Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HyperLightLogistics-Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HyperLightLogistics-Java.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Rauf Agaguliev
 */

package com.hll.hyperlightlogistics.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import proto.DeliveryOptionsServiceGrpc;
import proto.DeliveryRequest;
import proto.DeliveryResponse;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Service
public class GrpcClient {

    private final DeliveryOptionsServiceGrpc.DeliveryOptionsServiceBlockingStub blockingStub;
    private final ManagedChannel channel;

    public GrpcClient(@Value("${grpc.server.host}") String host,
                      @Value("${grpc.server.port}") int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = DeliveryOptionsServiceGrpc.newBlockingStub(channel);
    }

    public DeliveryResponse getDeliveryOptions(DeliveryRequest request) {
        return blockingStub.calculateDeliveryOptions(request);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
