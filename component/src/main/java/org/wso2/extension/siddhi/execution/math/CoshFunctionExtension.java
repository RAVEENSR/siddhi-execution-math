/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.math;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

import static org.wso2.extension.siddhi.execution.math.util.MathUtil.convertToDouble;

/**
 * cosh(a);
 * Returns the hyperbolic cosine of a (a is in radians).
 * Accept Type(s) :DOUBLE/INT/FLOAT/LONG
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "cosh",
        namespace = "math",
        description = "This function returns the hyperbolic cosine of `p1` which is in radians. It wraps " +
                "the `java.lang.Math.cosh()` function.",
        parameters = {
                @Parameter(
                        name = "p1",
                        description = "The value of the parameter whose hyperbolic cosine should be found. " +
                                "The input is required to be in radians.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE})},
        returnAttributes = @ReturnAttribute(
                description = "The hyperbolic cosine value of the input parameter.",
                type = {DataType.DOUBLE}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue double); \n" +
                        "from InValueStream \n" +
                        "select math:cosh(inValue) as cosValue \n" +
                        "insert into OutMediationStream;",
                description = "If the 'inValue' is given, the function calculates the hyperbolic cosine value for" +
                       " the same and directs the output to the output stream, OutMediationStream. For example, " +
                        "cosh (6d) returns 201.7156361224559.")
)
public class CoshFunctionExtension extends FunctionExecutor {
    @Override
    protected void init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:cosh() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the argument of math:cosh() " +
                    "function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;    // This method won't get called. Hence, unimplemented.
    }

    @Override
    protected Object execute(Object data) {
        if (data != null) {
            return Math.cosh(convertToDouble(data));
        }
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

    @Override
    public Map<String, Object> currentState() {
        return null;
    }

    @Override
    public void restoreState(Map<String, Object> map) {

    }
}
