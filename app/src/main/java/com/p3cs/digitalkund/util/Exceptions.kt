package com.p3cs.digitalkund.util

import java.io.IOException


class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)