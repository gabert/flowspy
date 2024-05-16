package com.github.gabert.deepflow.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class DeepFlowAgent {
    public static void premain(String argument,
                               Instrumentation instrumentation) {
        Advice advice = Advice.to(DeepFlowAdvice.class);
        new AgentBuilder.Default()
                .type(ElementMatchers.isAnnotatedWith(Marker.class))
                .transform((DynamicType.Builder<?> builder,
                            TypeDescription type,
                            ClassLoader loader,
                            JavaModule module,
                            ProtectionDomain pd) -> builder.visit(advice.on(ElementMatchers.isMethod())))
                .installOn(instrumentation);
    }
}