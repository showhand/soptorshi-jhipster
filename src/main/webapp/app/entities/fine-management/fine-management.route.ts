import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FineManagement } from 'app/shared/model/fine-management.model';
import { FineManagementService } from './fine-management.service';
import { FineManagementComponent } from './fine-management.component';
import { FineManagementDetailComponent } from './fine-management-detail.component';
import { FineManagementUpdateComponent } from './fine-management-update.component';
import { FineManagementDeletePopupComponent } from './fine-management-delete-dialog.component';
import { IFineManagement } from 'app/shared/model/fine-management.model';

@Injectable({ providedIn: 'root' })
export class FineManagementResolve implements Resolve<IFineManagement> {
    constructor(private service: FineManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFineManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FineManagement>) => response.ok),
                map((fineManagement: HttpResponse<FineManagement>) => fineManagement.body)
            );
        }
        return of(new FineManagement());
    }
}

export const fineManagementRoute: Routes = [
    {
        path: '',
        component: FineManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FineManagementDetailComponent,
        resolve: {
            fineManagement: FineManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FineManagementUpdateComponent,
        resolve: {
            fineManagement: FineManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FineManagementUpdateComponent,
        resolve: {
            fineManagement: FineManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fineManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FineManagementDeletePopupComponent,
        resolve: {
            fineManagement: FineManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
