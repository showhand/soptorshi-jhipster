import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPackaging, ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingExtendedService } from './commercial-packaging-extended.service';
import { CommercialPackagingExtendedComponent } from './commercial-packaging-extended.component';
import { CommercialPackagingDetailExtendedComponent } from './commercial-packaging-detail-extended.component';
import { CommercialPackagingUpdateExtendedComponent } from './commercial-packaging-update-extended.component';
import { CommercialPackagingDeletePopupExtendedComponent } from './commercial-packaging-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPackagingExtendedResolve implements Resolve<ICommercialPackaging> {
    constructor(private service: CommercialPackagingExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPackaging> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPackaging>) => response.ok),
                map((commercialPackaging: HttpResponse<CommercialPackaging>) => commercialPackaging.body)
            );
        }
        return of(new CommercialPackaging());
    }
}

export const commercialPackagingExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPackagingExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPackagingDetailExtendedComponent,
        resolve: {
            commercialPackaging: CommercialPackagingExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPackagingUpdateExtendedComponent,
        resolve: {
            commercialPackaging: CommercialPackagingExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPackagingUpdateExtendedComponent,
        resolve: {
            commercialPackaging: CommercialPackagingExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPackagingPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPackagingDeletePopupExtendedComponent,
        resolve: {
            commercialPackaging: CommercialPackagingExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
