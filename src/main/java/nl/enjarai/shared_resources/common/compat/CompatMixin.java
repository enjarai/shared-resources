package nl.enjarai.shared_resources.common.compat;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CompatMixin {
    String[] value() default {};

    class Visitor extends AnnotationVisitor {
        private String[] value;

        protected Visitor() {
            super(Opcodes.ASM9);
        }

        @Override
        public void visit(String name, Object value) {
            if (name.equals("value")) {
                this.value = (String[]) value;
            }
            super.visit(name, value);
        }

        public String[] getValue() {
            return value;
        }
    }
}
