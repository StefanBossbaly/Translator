#ifdef "RUNTIME_H"
#define "RUNTIME_H"

//Numeric Operations
inline int add(int num1, int num2);
inline int subtract(int num1, int num2);
inline int multiply(int num1, int num2);
inline int quotient(int num1, int num2);
inline int remainder(int num1, int num2);

//Rational Operations
inline int eq(int condition1, int condition2);
inline int neq(int condition1, int condition2);
inline int lt(int condition1, int condition2);
inline int lte(int condition1, int condition2);
inline int gt(int condition1, int condition2);
inline int gte(int condition1, int condition2);

//Logical Operations
inline int not(int condition);
inline int and(int condition1, int condition2);
inline int or(int condition1, int condition2);

#endif
