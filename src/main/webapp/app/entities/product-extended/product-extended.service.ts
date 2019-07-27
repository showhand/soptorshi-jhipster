import { ProductService } from 'app/entities/product';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductExtendedService extends ProductService {
    public page: number;
    public previousPage: number;
    public reverse: string;
    public predicate: string;

    constructor(protected http: HttpClient) {
        super(http);
    }
}
