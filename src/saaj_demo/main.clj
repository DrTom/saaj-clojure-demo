(ns saaj-demo.main
  (:import 
    [javax.xml.soap SOAPConnectionFactory MessageFactory]
    [java.io ByteArrayOutputStream]
    )
  (:require 
    [cider-ci.utils.debug :as debug]
    [clojure.tools.logging :as logging]
    ))

(defn create-request-message [email-address]
  (let [message (-> (MessageFactory/newInstance) .createMessage)
        url "http://ws.cdyne.com/"
        envelope (doto (-> message .getSOAPPart .getEnvelope)
                   (.addNamespaceDeclaration "example", url ))
        body (.getBody envelope)]

    (let [body-el (.addChildElement body "VerifyEmail" "example")]
      (doto (.addChildElement body-el "email" "example")
        (.addTextNode email-address))
      (doto (.addChildElement body-el "LicenseKey" "example")
        (.addTextNode "123")))

    (doto (.getMimeHeaders message)
      (.addHeader "SOAPAction" (str url "VerifyEmail")))

    (.saveChanges message)
    message))


(defn create-connection []
  (.createConnection 
    (SOAPConnectionFactory/newInstance)))

(defn capture-as-string [writable]
  (with-open [os (ByteArrayOutputStream.)]
    (.writeTo writable os)
    (.toString os)))

(defn -main []
  (println "Enter email-address to be verified: ")
 
  (let [email-address (.readLine *in*) 
        endpoint "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx"
        request-message  (create-request-message email-address)
        request-message-str (capture-as-string request-message)]

    (logging/info "THE MESSAGE: " request-message-str)

    (let [ response (-> (create-connection)
                        (.call request-message endpoint))
          response-str (capture-as-string response) ]

      (logging/info "THE RESPONSE" response-str))))


;### Debug ####################################################################
;(logging-config/set-logger! :level :debug)
;(logging-config/set-logger! :level :info)
;(debug/debug-ns *ns*)
