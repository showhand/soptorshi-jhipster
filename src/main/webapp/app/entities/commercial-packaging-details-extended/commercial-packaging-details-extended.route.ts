import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPackagingDetails, ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsExtendedService } from './commercial-packaging-details-extended.service';
import { CommercialPackagingDetailsExtendedComponent } from './commercial-packaging-details-extended.component';
import { CommercialPackagingDetailsDetailExtendedComponent } from './commercial-packaging-details-detail-extended.component';
import { CommercialPackagingDetailsUpdateExtendedComponent } from './commercial-packaging-details-update-extended.component';
import { CommercialPackagingDetailsDeletePopupExtendedComponent } from './commercial-packaging-details-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPackagingDetailsExtendedResolve implements Resolve<ICommercialPackagingDetails> {
    constructor(private service: CommercialPackagingDetailsExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPackagingDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPackagingDetails>) => response.ok),
                map((commercialPackagingDetails: HttpResponse<CommercialPackagingDetails>) => commercialPackagingDetails.body)
            );
        }
        return of(new CommercialPackagingDetails());
    }
}

export const commercialPackagingDetailsExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPackagingDetailsExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPackagingDetailsDetailExtendedComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPackagingDetailsUpdateExtendedComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPackagingDetailsUpdateExtendedComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPackagingDetailsPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPackagingDetailsDeletePopupExtendedComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
