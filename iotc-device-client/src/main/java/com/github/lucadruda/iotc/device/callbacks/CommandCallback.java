// Copyright (c) Luca Druda. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
package com.github.lucadruda.iotc.device.callbacks;


import com.github.lucadruda.iotc.device.models.IoTCCommand;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData;

@FunctionalInterface
public interface CommandCallback extends IoTCCallback {
    DeviceMethodData exec(IoTCCommand result);
}