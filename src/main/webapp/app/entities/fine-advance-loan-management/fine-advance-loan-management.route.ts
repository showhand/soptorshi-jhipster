import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';
import { FineAdvanceLoanManagementComponent } from './fine-advance-loan-management.component';
import { FineAdvanceLoanManagementDetailComponent } from './fine-advance-loan-management-detail.component';
import { FineAdvanceLoanManagementUpdateComponent } from './fine-advance-loan-management-update.component';
import { FineAdvanceLoanManagementDeletePopupComponent } from './fine-advance-loan-management-delete-dialog.component';
import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

@Injectable({ providedIn: 'root' })
export class FineAdvanceLoanManagementResolve implements Resolve<IFineAdvanceLoanManagement> {
    constructor(private service: FineAdvanceLoanManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFineAdvanceLoanManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FineAdvanceLoanManagement>) => response.ok),
                map((fineAdvanceLoanManagement: HttpResponse<FineAdvanceLoanManagement>) => fineAdvanceLoanManagement.body)
            );
        }
        return of(new FineAdvanceLoanManagement());
    }
}

export const fineAdvanceLoanManagementRoute: Routes = [
    {
        path: '',
        component: FineAdvanceLoanManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FineAdvanceLoanManagementDetailComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FineAdvanceLoanManagementUpdateComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FineAdvanceLoanManagementUpdateComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fineAdvanceLoanManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FineAdvanceLoanManagementDeletePopupComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
