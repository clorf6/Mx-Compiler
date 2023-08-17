#include<stdio.h>
#include<malloc.h>
#include<string.h>

#define bool _Bool

void print(char *str) { 
    printf("%s", str); 
}

void println(char *str) { 
    printf("%s\n", str); 
}

void printInt(int n) { 
    printf("%d", n); 
}

void printlnInt(int n) { 
    printf("%d\n", n); 
}

char *getString() {
    char *str = (char *)malloc(256);
    scanf("%s", str);
    return str;
}

int getInt() {
    int n;
    scanf("%d", &n);
    return n;
}

char *toString(int n) {
    char *str = malloc(256);
    sprintf(str, "%d", n);
    return str;
}

char* string_substring(char *str, int left, int right) {
    char *s = malloc(right - left + 1);
    for (int i = left; i < right; i++) 
        s[i - left] = str[i];
    s[right - left] = '\0';
    return s;
}

int string_parseInt(char *str) {
    int n;
    sscanf(str, "%d", &n);
    return n;
}

int string_ord(char *str, int pos) { 
    return str[pos]; 
}

char* string_add(char *lhs, char *rhs) {
  char *s = malloc(strlen(lhs) + strlen(rhs) + 1);
  strcpy(s, lhs);
  strcat(s, rhs);
  return s;
}

bool string_le(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) < 0; 
}

bool string_leq(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) <= 0; 
}

bool string_ge(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) > 0; 
}

bool string_geq(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) >= 0; 
}

bool string_eq(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) == 0; 
}

bool string_neq(char *lhs, char *rhs) { 
    return strcmp(lhs, rhs) != 0; 
}