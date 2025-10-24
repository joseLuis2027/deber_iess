package com.jose.service;

public class SaldoNoDisponibleException extends RuntimeException {
  public SaldoNoDisponibleException(String msg) { super(msg); }
}
