//package com.project.zipsa.unit.mq;
//
//import com.github.fridujo.rabbitmq.mock.AmqArguments;
//import com.github.fridujo.rabbitmq.mock.ReceiverRegistry;
//import com.github.fridujo.rabbitmq.mock.exchange.BindableMockExchange;
//import com.github.fridujo.rabbitmq.mock.exchange.TypedMockExchangeCreator;
//
//@FunctionalInterface
//public interface MockExchangeCreator {
//
//   static TypedMockExchangeCreator creatorWithExchangeType(String type, com.github.fridujo.rabbitmq.mock.exchange.MockExchangeCreator creator) {
//       return new TypedMockExchangeCreatorImpl(type, creator);
//   }
//
//   BindableMockExchange createMockExchange(String exchangeName, AmqArguments arguments, ReceiverRegistry receiverRegistry);
//
//   public final class TypedMockExchangeCreatorImpl implements TypedMockExchangeCreator {
//       private final String type;
//       private final com.github.fridujo.rabbitmq.mock.exchange.MockExchangeCreator creator;
//
//       public TypedMockExchangeCreatorImpl(String type, com.github.fridujo.rabbitmq.mock.exchange.MockExchangeCreator creator) {
//           this.type = type;
//           this.creator = creator;
//       }
//
//       @Override
//       public String getType() {
//           return type;
//       }
//
//       @Override
//       public BindableMockExchange createMockExchange(String exchangeName, AmqArguments arguments, ReceiverRegistry receiverRegistry) {
//           return creator.createMockExchange(exchangeName, arguments, receiverRegistry);
//       }
//   }
//
//}
