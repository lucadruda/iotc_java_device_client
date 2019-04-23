// Copyright (c) Luca Druda. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
package com.github.lucadruda.iotc.device.callbacks;

public abstract class IoTCCallback {
    /**
     * Execute the callback
     * @param result result of the operation triggering the callback
     */
    public abstract void Exec(Object result);

}