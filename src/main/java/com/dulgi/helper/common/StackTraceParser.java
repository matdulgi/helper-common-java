//
////	static Log log = Log.ENABLE;
//	static Log log = Log.DISABLE;
//
//	public static void disableLog() {
//		log = Log.DISABLE;
//	}
//
//	public static void enableBasicLog() {
//		log = Log.BASIC;
//	}
//
//	public static void enableDetailLog() {
//		log = Log.DETAIL;
//	}
//
//	public static void logExecuteMethod() {
//		if (log != Log.DISABLE) {
//			System.out.println("execute method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//		}
//	}
//
//	public static void logExecuteMethod(Object argument) {
//		if (log != Log.DISABLE) {
//			System.out.println("execute method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//			System.out.println("argument : " + argument);
//		}
//	}
//
//	// 하고싶은 말이 있으면 람다로 출력..
//	private static void logExecuteMethod(Consumer consumer) {
//		if (log != Log.DISABLE) {
//			System.out.println("execute method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//			consumer.accept(consumer);
//		}
//	}
//
//	private static void logEndMethod() {
//		if (log != Log.DISABLE) {
//			System.out.println("end method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//		}
//	}
//
//	private static void logEndMethod(Object returnValue) {
//		if (log != Log.DISABLE) {
//			System.out.println("return : " + returnValue);
//			System.out.println("end method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//		}
//	}
//
//	private static void logEndMethod(Consumer<Object> consumer) {
//		if (log != Log.DISABLE) {
//			consumer.accept(consumer);
//			System.out.println("end method : " + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName());
//		}
//	}
//
//
//enum Log {
//	DISABLE, BASIC, DETAIL
//}