// ------------------------------------ REACT ------------------------------------
export type ReactFunctionOrComponentClass<P> = React.ComponentClass<P> | React.StatelessComponent<P>;

// --------------------------------------
export type OwnerData = {
  firstName: string;
  lastName: string;
  address: string;
  city: string;
  telephone: string;
};

export type PetData = {
  name: string;
  birthDate: string;
  type: number;
};

export type PetType = {
  id: number;
  name: string;
};

export type VisitData = {
  description: string;
  date: string;
};
