/* tslint:disable */
//  This file was automatically generated and should not be edited.

export type AddOwnerInput = {
  firstName?: string | null,
  lastName?: string | null,
  address?: string | null,
  city?: string | null,
  telephone?: string | null,
};

export type UpdateOwnerInput = {
  ownerId: number,
  firstName?: string | null,
  lastName?: string | null,
  address?: string | null,
  city?: string | null,
  telephone?: string | null,
};

export type AddPetInput = {
  ownerId: number,
  name: string,
  birthDate: string,
  typeId: number,
};

export type UpdatePetInput = {
  petId: number,
  name?: string | null,
  birthDate?: string | null,
  typeId?: number | null,
};

export type AddSpecialtyInput = {
  name: string,
};

export type RemoveSpecialtyInput = {
  specialtyId: number,
};

export type UpdateSpecialtyInput = {
  specialtyId: number,
  name: string,
};

export type AddVisitInput = {
  petId: number,
  description: string,
  date: string,
};

export type AddOwnerMutationVariables = {
  input: AddOwnerInput,
};

export type AddOwnerMutation = {
  addOwner:  {
    owner:  {
      id: number,
      firstName: string,
      lastName: string,
      address: string,
      city: string,
      telephone: string,
    },
  },
};

export type OwnerListQuery = {
  // Return all known Pet Owners
  owners:  Array< {
    id: number,
    firstName: string,
    lastName: string,
    address: string,
    city: string,
    telephone: string,
    pets:  Array< {
      name: string,
      visits:  {
        totalCount: number,
      },
    } >,
  } >,
};

export type OwnerQueryVariables = {
  ownerId: number,
};

export type OwnerQuery = {
  owner:  {
    id: number,
    firstName: string,
    lastName: string,
    address: string,
    city: string,
    telephone: string,
    pets:  Array< {
      id: number,
      name: string,
      birthDate: string,
      type:  {
        id: number,
        name: string,
      },
      visits:  {
        visits:  Array< {
          id: number,
          date: string,
          description: string,
        } >,
      },
    } >,
  },
};

export type UpdateOwnerMutationVariables = {
  input: UpdateOwnerInput,
};

export type UpdateOwnerMutation = {
  updateOwner:  {
    owner:  {
      id: number,
      firstName: string,
      lastName: string,
      address: string,
      city: string,
      telephone: string,
    },
  },
};

export type AddPetMutationVariables = {
  input: AddPetInput,
};

export type AddPetMutation = {
  addPet:  {
    pet:  {
      id: number,
    },
  },
};

export type GetAddPetFormDataQueryVariables = {
  ownerId: number,
};

export type GetAddPetFormDataQuery = {
  owner:  {
    id: number,
    lastName: string,
    firstName: string,
  },
  // Return all known PetTypes
  pettypes:  Array< {
    id: number,
    name: string,
  } >,
};

export type GetUpdatePetFormDataQueryVariables = {
  petId: number,
};

export type GetUpdatePetFormDataQuery = {
  pet:  {
    id: number,
    name: string,
    birthDate: string,
    type:  {
      id: number,
    },
    owner:  {
      id: number,
      lastName: string,
      firstName: string,
    },
  },
  // Return all known PetTypes
  pettypes:  Array< {
    id: number,
    name: string,
  } >,
};

export type UpdatePetMutationVariables = {
  input: UpdatePetInput,
};

export type UpdatePetMutation = {
  updatePet:  {
    pet:  {
      id: number,
      name: string,
      birthDate: string,
      type:  {
        id: number,
      },
    },
  },
};

export type AddSpecialtyMutationVariables = {
  input: AddSpecialtyInput,
};

export type AddSpecialtyMutation = {
  addSpecialty:  {
    specialty:  {
      id: number,
      name: string,
    },
  },
};

export type LoadSpecialtiesQuery = {
  specialties:  Array< {
    id: number,
    name: string,
  } >,
};

export type RemoveSpecialtyMutationVariables = {
  input: RemoveSpecialtyInput,
};

export type RemoveSpecialtyMutation = {
  removeSpecialty:  {
    specialties:  Array< {
      id: number,
      name: string,
    } >,
  },
};

export type UpdateSpecialtyMutationVariables = {
  input: UpdateSpecialtyInput,
};

export type UpdateSpecialtyMutation = {
  updateSpecialty:  {
    specialty:  {
      id: number,
      name: string,
    },
  },
};

export type VetsQuery = {
  // Return all known veterinaries
  vets:  Array< {
    id: number,
    firstName: string,
    lastName: string,
    specialties:  Array< {
      id: number,
      name: string,
    } >,
  } >,
};

export type AddVisitMutationVariables = {
  input: AddVisitInput,
};

export type AddVisitMutation = {
  addVisit:  {
    visit:  {
      id: number,
    },
  },
};

export type LoadAddVisitPageDataQueryVariables = {
  petId: number,
};

export type LoadAddVisitPageDataQuery = {
  pet:  {
    id: number,
    name: string,
    birthDate: string,
    type:  {
      id: number,
      name: string,
    },
    owner:  {
      lastName: string,
      firstName: string,
    },
  },
};

export type OwnerFragment = {
  id: number,
  firstName: string,
  lastName: string,
  address: string,
  city: string,
  telephone: string,
};

export type OwnerDetailsFragment = {
  id: number,
  firstName: string,
  lastName: string,
  address: string,
  city: string,
  telephone: string,
  pets:  Array< {
    id: number,
    name: string,
    birthDate: string,
    type:  {
      id: number,
      name: string,
    },
    visits:  {
      visits:  Array< {
        id: number,
        date: string,
        description: string,
      } >,
    },
  } >,
};

export type PetFragment = {
  id: number,
  name: string,
  birthDate: string,
  type:  {
    id: number,
    name: string,
  },
};

export type PetDetailsFragment = {
  id: number,
  name: string,
  birthDate: string,
  type:  {
    id: number,
    name: string,
  },
  visits:  {
    visits:  Array< {
      id: number,
      date: string,
      description: string,
    } >,
  },
};

export type OwnerSummaryFragment = {
  id: number,
  firstName: string,
  lastName: string,
  address: string,
  city: string,
  telephone: string,
  pets:  Array< {
    name: string,
    visits:  {
      totalCount: number,
    },
  } >,
};

export type VetFragment = {
  id: number,
  firstName: string,
  lastName: string,
  specialties:  Array< {
    id: number,
    name: string,
  } >,
};
/* tslint:enable */
