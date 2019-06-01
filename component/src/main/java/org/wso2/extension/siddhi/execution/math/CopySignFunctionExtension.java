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
 * copysign(magnitude,sign);
 * Returns the first argument with the sign of the second argument.
 * magnitude - Accept Type(s):DOUBLE/INT/FLOAT/LONG
 * sign - Accept Type(s):DOUBLE/INT/FLOAT/LONG
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "copySign",
        namespace = "math",
        description = "This function returns a value of an input with the received `magnitude` and `sign` " +
                "of another input. It wraps the `java.lang.Math.copySign()` function.",
        parameters = {
                @Parameter(
                        name = "magnitude",
                        description = "The magnitude of this parameter is used in the output attribute.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE}),
                @Parameter(
                        name = "sign",
                        description = "The sign of this parameter is used in the output attribute.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE}),
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns the first argument with the sign of the second argument.",
                type = {DataType.DOUBLE}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue1 double, inValue2 double); \n" +
                        "from InValueStream \n" +
                        "select math:copySign(inValue1,inValue2) as copysignValue \n" +
                        "insert into OutMediationStream;",
                description = "If two values are provided as 'inValue1' and 'inValue2', the function copies " +
                               "the magnitude and sign of the second argument into the first one and directs " +
                               "the result to the output stream, OutMediatonStream. For example, " +
                               "copySign(5.6d, -3.0d) returns -5.6.")
)
public class CopySignFunctionExtension extends FunctionExecutor {
    @Override
    protected void init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:copysign() function, " +
                    "required 2, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the first argument of " +
                    "math:copysign() function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
        attributeType = attributeExpressionExecutors[1].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the second argument of " +
                    "math:copysign() function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
    }

    @Override
    protected Object execute(Object[] data) {
        if (data[0] != null && data[1] != null) {
            return Math.copySign(convertToDouble(data[0]), convertToDouble(data[1]));
        }
        return null;
    }

    @Override
    protected Object execute(Object data) {
        return null;    // This method won't get called. Hence, unimplemented.
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
