(ns pianolo.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [pianolo.core-test]
   [pianolo.common-test]))

(enable-console-print!)

(doo-tests 'pianolo.core-test
           'pianolo.common-test)
