export interface IManufacturer {
    id?: number;
    name?: string;
    contact?: string;
    email?: string;
    address?: string;
    description?: string;
    remarks?: string;
}

export class Manufacturer implements IManufacturer {
    constructor(
        public id?: number,
        public name?: string,
        public contact?: string,
        public email?: string,
        public address?: string,
        public description?: string,
        public remarks?: string
    ) {}
}
