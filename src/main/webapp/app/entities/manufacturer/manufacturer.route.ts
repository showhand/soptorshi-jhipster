import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Manufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from './manufacturer.service';
import { ManufacturerComponent } from './manufacturer.component';
import { ManufacturerDetailComponent } from './manufacturer-detail.component';
import { ManufacturerUpdateComponent } from './manufacturer-update.component';
import { ManufacturerDeletePopupComponent } from './manufacturer-delete-dialog.component';
import { IManufacturer } from 'app/shared/model/manufacturer.model';

@Injectable({ providedIn: 'root' })
export class ManufacturerResolve implements Resolve<IManufacturer> {
    constructor(private service: ManufacturerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IManufacturer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Manufacturer>) => response.ok),
                map((manufacturer: HttpResponse<Manufacturer>) => manufacturer.body)
            );
        }
        return of(new Manufacturer());
    }
}

export const manufacturerRoute: Routes = [
    {
        path: '',
        component: ManufacturerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManufacturerDetailComponent,
        resolve: {
            manufacturer: ManufacturerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManufacturerUpdateComponent,
        resolve: {
            manufacturer: ManufacturerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManufacturerUpdateComponent,
        resolve: {
            manufacturer: ManufacturerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const manufacturerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ManufacturerDeletePopupComponent,
        resolve: {
            manufacturer: ManufacturerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
