import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Manufacturer } from 'app/shared/model/manufacturer.model';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerDeletePopupComponentExtended } from 'app/entities/manufacturer-extended/manufacturer-delete-dialog.component.extended';
import { ManufacturerServiceExtended } from 'app/entities/manufacturer-extended/manufacturer.service.extended';
import { ManufacturerComponentExtended } from 'app/entities/manufacturer-extended/manufacturer.component.extended';
import { ManufacturerDetailComponentExtended } from 'app/entities/manufacturer-extended/manufacturer-detail.component.extended';
import { ManufacturerUpdateComponentExtended } from 'app/entities/manufacturer-extended/manufacturer-update.component.extended';

@Injectable({ providedIn: 'root' })
export class ManufacturerResolveExtended implements Resolve<IManufacturer> {
    constructor(private service: ManufacturerServiceExtended) {}

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

export const manufacturerRouteExtended: Routes = [
    {
        path: '',
        component: ManufacturerComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManufacturerDetailComponentExtended,
        resolve: {
            manufacturer: ManufacturerResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManufacturerUpdateComponentExtended,
        resolve: {
            manufacturer: ManufacturerResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManufacturerUpdateComponentExtended,
        resolve: {
            manufacturer: ManufacturerResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const manufacturerPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: ManufacturerDeletePopupComponentExtended,
        resolve: {
            manufacturer: ManufacturerResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
