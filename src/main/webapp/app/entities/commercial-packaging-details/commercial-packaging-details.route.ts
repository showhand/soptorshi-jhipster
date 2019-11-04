import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPackagingDetails, ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsService } from './commercial-packaging-details.service';
import { CommercialPackagingDetailsComponent } from './commercial-packaging-details.component';
import { CommercialPackagingDetailsDetailComponent } from './commercial-packaging-details-detail.component';
import { CommercialPackagingDetailsUpdateComponent } from './commercial-packaging-details-update.component';
import { CommercialPackagingDetailsDeletePopupComponent } from './commercial-packaging-details-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialPackagingDetailsResolve implements Resolve<ICommercialPackagingDetails> {
    constructor(private service: CommercialPackagingDetailsService) {}

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

export const commercialPackagingDetailsRoute: Routes = [
    {
        path: '',
        component: CommercialPackagingDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPackagingDetailsDetailComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPackagingDetailsUpdateComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPackagingDetailsUpdateComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPackagingDetailsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPackagingDetailsDeletePopupComponent,
        resolve: {
            commercialPackagingDetails: CommercialPackagingDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagingDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
