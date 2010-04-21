/*
 * Copyright 2010 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package voldemort.store.routed;

import voldemort.VoldemortException;
import voldemort.cluster.Node;
import voldemort.store.nonblockingstore.NonblockingStore;
import voldemort.store.nonblockingstore.NonblockingStoreCallback;
import voldemort.store.routed.Pipeline.Event;

/**
 * BasicResponseCallback ties together the {@link NonblockingStore} asynchronous
 * callback mechanism and the {@link Pipeline}. When the requestComplete method
 * is called, an {@link Pipeline.Event#RESPONSE_RECEIVED} event is issued with a
 * newly-created {@link Response} instance.
 * 
 * @param <K> Type for the key used in the response
 * 
 * @see NonblockingStoreCallback
 * @see Response
 */

public class BasicResponseCallback<K> implements NonblockingStoreCallback {

    private final Pipeline pipeline;

    private final Node node;

    private final K key;

    public BasicResponseCallback(Pipeline pipeline, Node node, K key) {
        this.pipeline = pipeline;
        this.node = node;
        this.key = key;
    }

    public void requestComplete(Object result, long requestTime) throws VoldemortException {
        Response<K, Object> response = new Response<K, Object>(node, key, result, requestTime);
        pipeline.addEvent(Event.RESPONSE_RECEIVED, response);
    }

}
