import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IManufacturer, Manufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerExtendedService } from 'app/entities/manufacturer-extended/manufacturer-extended.service';
import { ManufacturerExtendedComponent } from 'app/entities/manufacturer-extended/manufacturer-extended.component';
import { ManufacturerDetailExtendedComponent } from 'app/entities/manufacturer-extended/manufacturer-detail-extended.component';
import { ManufacturerUpdateExtendedComponent } from 'app/entities/manufacturer-extended/manufacturer-update-extended.component';
import { ManufacturerDeletePopupExtendedComponent } from 'app/entities/manufacturer-extended/manufacturer-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class ManufacturerExtendedResolve implements Resolve<IManufacturer> {
    constructor(private service: ManufacturerExtendedService) {}

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

export const manufacturerExtendedRoute: Routes = [
    {
        path: '',
        component: ManufacturerExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManufacturerDetailExtendedComponent,
        resolve: {
            manufacturer: ManufacturerExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManufacturerUpdateExtendedComponent,
        resolve: {
            manufacturer: ManufacturerExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManufacturerUpdateExtendedComponent,
        resolve: {
            manufacturer: ManufacturerExtendedResolve
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
        component: ManufacturerDeletePopupExtendedComponent,
        resolve: {
            manufacturer: ManufacturerExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Manufacturers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
