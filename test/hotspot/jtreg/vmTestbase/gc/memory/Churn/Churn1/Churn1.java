/*
 * Copyright (c) 2002, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


/*
 * @test
 * @key stress gc
 *
 * @summary converted from VM Testbase gc/memory/Churn/Churn1.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run driver jdk.test.lib.FileInstaller . .
 * @run main/othervm gc.memory.Churn.Churn1.Churn1
 */

package gc.memory.Churn.Churn1;

import nsk.share.test.*;
import nsk.share.gc.*;

/**
 *  Test that GC works with memory that is churn over.
 *
 *  This test starts a number of threads that create objects,
 *  keep references to them in array and overwrite them. The test
 *  test checks that GC is able to collect these objects.
 *
 *  This test will adjust the size of allocated objects to run
 *  environment.
 *
 *  @see nsk.share.gc.RunParams#getTestMemory()
 */
public class Churn1 extends ThreadedGCTest {
        private int multiplier = 10;
        private int sizeOfArray;

        class ThreadObject implements Runnable {
                private AllMemoryObject objectArray[] = new AllMemoryObject[sizeOfArray];

                public ThreadObject() {
                        for (int i = 0; i < sizeOfArray; i ++)
                                objectArray[i] = new AllMemoryObject(multiplier * i);
                }

                public void run() {
                        int index = LocalRandom.nextInt(sizeOfArray);
                        objectArray[index] = new AllMemoryObject(multiplier * index);
                }
        }

        protected Runnable createRunnable(int i) {
                return new ThreadObject();
        }
        public void run() {
                sizeOfArray = (int) Math.min(Math.sqrt(runParams.getTestMemory() * 2 / runParams.getNumberOfThreads() / multiplier), Integer.MAX_VALUE);
                super.run();
        }

        public static void main(String args[]) {
                GC.runTest(new Churn1(), args);
        }
}
