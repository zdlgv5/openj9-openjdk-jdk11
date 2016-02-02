/*
 * Copyright (c) 2001, 2015, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4341304 4485668 4966728 8032066
 * @summary Test that methods readResolve and writeReplace show
 * up in serialized-form.html the same way that readObject and writeObject do.
 * If the doclet includes readResolve and writeReplace in the serialized-form
 * documentation that same way the it includes readObject and writeObject, the
 * test passes.  This also tests that throws tag information is correctly shown
 * in the serialized form page.
 * Make sure see tags work in serialized form.
 * @author jamieh
 * @library ../lib/
 * @modules jdk.javadoc
 * @build JavadocTester
 * @build TestSerializedForm
 * @run main TestSerializedForm
 */

import java.io.*;

public class TestSerializedForm extends JavadocTester implements Serializable {
    public static void main(String... args) throws Exception {
        TestSerializedForm tester = new TestSerializedForm();
        tester.runTests();
//        tester.run(ARGS, TEST, NEGATED_TEST);
//        tester.run(ARGS_PRIVATE, TEST_PRIVATE, NEGATED_TEST_PRIVATE);
//        tester.printSummary();
    }

    @Test
    void testDefault() {
        javadoc("-d", "out-default",
                "-sourcepath", testSrc,
                testSrc("TestSerializedForm.java"), "pkg1");
        checkExit(Exit.OK);

        checkOutput("serialized-form.html", true,
                "protected&nbsp;java.lang.Object&nbsp;readResolve()",
                "protected&nbsp;java.lang.Object&nbsp;writeReplace()",
                "protected&nbsp;java.lang.Object&nbsp;readObjectNoData()",
                "See Also",
                "<h3>Class pkg1.NestedInnerClass.InnerClass.ProNestedInnerClass "
                + "extends java.lang.Object implements Serializable</h3>",
                "<h3>Class pkg1.PrivateIncludeInnerClass.PriInnerClass extends "
                + "java.lang.Object implements Serializable</h3>",
                "<h3>Class pkg1.ProtectedInnerClass.ProInnerClass extends "
                + "java.lang.Object implements Serializable</h3>");

        checkOutput("serialized-form.html", false,
                "<h3>Class <a href=\"pkg1/NestedInnerClass.InnerClass.ProNestedInnerClass.html\" "
                + "title=\"class in pkg1\">pkg1.NestedInnerClass.InnerClass.ProNestedInnerClass</a> "
                + "extends java.lang.Object implements Serializable</h3>",
                "<h3>Class <a href=\"pkg1/PrivateInnerClass.PriInnerClass.html\" title=\"class in pkg1\">"
                + "pkg1.PrivateInnerClass.PriInnerClass</a> extends java.lang.Object implements Serializable</h3>",
                "<h3>Class <a href=\"pkg1/ProtectedInnerClass.ProInnerClass.html\" title=\"class in pkg1\">"
                + "pkg1.ProtectedInnerClass.ProInnerClass</a> extends java.lang.Object implements Serializable</h3>",
                "<h3>Class pkg1.PublicExcludeInnerClass.PubInnerClass extends java.lang.Object implements "
                + "Serializable</h3>");
    }

    @Test
    void testPrivate() {
        javadoc("-private",
                "-d", "out-private",
                "-sourcepath", testSrc,
                testSrc("TestSerializedForm.java"), "pkg1");
        checkExit(Exit.OK);

        checkOutput("serialized-form.html", true,
                "<h3>Class <a href=\"pkg1/NestedInnerClass.InnerClass.ProNestedInnerClass.html\" "
                + "title=\"class in pkg1\">pkg1.NestedInnerClass.InnerClass.ProNestedInnerClass</a> "
                + "extends java.lang.Object implements Serializable</h3>",
                "<h3>Class <a href=\"pkg1/PrivateIncludeInnerClass.PriInnerClass.html\" title=\"class in pkg1\">"
                + "pkg1.PrivateIncludeInnerClass.PriInnerClass</a> extends java.lang.Object implements Serializable</h3>",
                "<h3>Class <a href=\"pkg1/ProtectedInnerClass.ProInnerClass.html\" title=\"class in pkg1\">"
                + "pkg1.ProtectedInnerClass.ProInnerClass</a> extends java.lang.Object implements Serializable</h3>");

        checkOutput("serialized-form.html", false,
                "<h3>Class pkg1.NestedInnerClass.InnerClass.ProNestedInnerClass "
                + "extends java.lang.Object implements Serializable</h3>",
                "<h3>Class pkg1.PrivateInnerClass.PriInnerClass extends "
                + "java.lang.Object implements Serializable</h3>",
                "<h3>Class pkg1.ProtectedInnerClass.ProInnerClass extends "
                + "java.lang.Object implements Serializable</h3>",
                "<h3>Class <a href=\"pkg1/PublicExcludeInnerClass.PubInnerClass.html\" "
                + "title=\"class in pkg1\">pkg1.PublicExcludeInnerClass.PubInnerClass</a> "
                + "extends java.lang.Object implements Serializable</h3>");
    }

    /**
     * @serial
     * @see TestSerializedForm
     */
    public final int SERIALIZABLE_CONSTANT = 1;

    /**
     * The entry point of the test.
     * @param args the array of command line arguments.
     */

    /**
     * @param s ObjectInputStream.
     * @throws IOException when there is an I/O error.
     * @serial
     */
    private void readObject(ObjectInputStream s) throws IOException {}

    /**
     * @param s ObjectOutputStream.
     * @throws IOException when there is an I/O error.
     * @serial
     */
    private void writeObject(ObjectOutputStream s) throws IOException {}

    /**
     * @throws IOException when there is an I/O error.
     * @serialData This is a serial data comment.
     * @return an object.
     */
    protected Object readResolve() throws IOException {return null;}

    /**
     * @throws IOException when there is an I/O error.
     * @serialData This is a serial data comment.
     * @return an object.
     */
    protected Object writeReplace() throws IOException {return null;}

    /**
     * @throws IOException when there is an I/O error.
     * @serialData This is a serial data comment.
     * @return an object.
     */
    protected Object readObjectNoData() throws IOException {
        return null;
    }

}
