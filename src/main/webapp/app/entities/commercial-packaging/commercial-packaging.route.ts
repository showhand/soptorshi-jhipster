import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from './commercial-packaging.service';
import { CommercialPackagingComponent } from './commercial-packaging.component';
import { CommercialPackagingDetailComponent } from './commercial-packaging-detail.component';
import { CommercialPackagingUpdateComponent } from './commercial-packaging-update.component';
import { CommercialPackagingDeletePopupComponent } from './commercial-packaging-delete-dialog.component';
import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';

@Injectable({ providedIn: 'root' })
export class CommercialPackagingResolve implements Resolve<ICommercialPackaging> {
    constructor(private service: CommercialPackagingService) {}

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

export const commercialPackagingRoute: Routes = [
    {
        path: '',
        component: CommercialPackagingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPackagingDetailComponent,
        resolve: {
            commercialPackaging: CommercialPackagingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPackagingUpdateComponent,
        resolve: {
            commercialPackaging: CommercialPackagingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPackagingUpdateComponent,
        resolve: {
            commercialPackaging: CommercialPackagingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPackagingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPackagingDeletePopupComponent,
        resolve: {
            commercialPackaging: CommercialPackagingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPackagings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
