import * as React from "react";
import { QueryProps } from "react-apollo";

type ReactFunctionOrComponentClass<P> = React.ComponentClass<P> | React.StatelessComponent<P>;

const withLoadingHandler = <P extends { data: QueryProps }>(TheComponent: ReactFunctionOrComponentClass<P>) => {
  const LoadingHandlerWrapper = (props: P) => (props.data.loading ? <h1>Loading</h1> : <TheComponent {...props} />);
  return LoadingHandlerWrapper;
};

export default withLoadingHandler;
