package ru.cultserv.adv.yandex.direct.methods.impl;

import com.google.common.base.Function;
import com.google.common.reflect.Reflection;
import ru.cultserv.adv.yandex.direct.methods.DirectMethod;
import ru.cultserv.adv.yandex.direct.methods.MethodName;
import ru.cultserv.adv.yandex.direct.util.requests.YandexDirectMethodCaller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Alexandr Kolosov
 * @since 7/3/14
 */
public class ProxyBuilder {

	private static class Handler implements InvocationHandler {

		private String name;
		private YandexDirectMethodCaller caller;

		private Handler(String name, YandexDirectMethodCaller caller) {
			this.name = name;
			this.caller = caller;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("toString") && args == null) {
				return name;
			}

			DirectMethod annotation = method.getAnnotation(DirectMethod.class);
			if (annotation == null) {
				throw new IllegalStateException("no annotation on method " + method.getName());
			}

			MethodName methodName = annotation.value();
			Function<Object[], Object> converter = methodName.getConverter();
			if (converter != null) {
				return caller.call(methodName, converter.apply(args));
			}

			return caller.call(methodName, args);
		}
	}

	public static <T> T create(Class<T> targetInterface, YandexDirectMethodCaller caller) {
		String name = targetInterface.getName();
		return Reflection.newProxy(targetInterface, new Handler(name, caller));
	}
}
