import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProvidentManagement } from 'app/shared/model/provident-management.model';
import { ProvidentManagementService } from './provident-management.service';
import { ProvidentManagementComponent } from './provident-management.component';
import { ProvidentManagementDetailComponent } from './provident-management-detail.component';
import { ProvidentManagementUpdateComponent } from './provident-management-update.component';
import { ProvidentManagementDeletePopupComponent } from './provident-management-delete-dialog.component';
import { IProvidentManagement } from 'app/shared/model/provident-management.model';

@Injectable({ providedIn: 'root' })
export class ProvidentManagementResolve implements Resolve<IProvidentManagement> {
    constructor(private service: ProvidentManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProvidentManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProvidentManagement>) => response.ok),
                map((providentManagement: HttpResponse<ProvidentManagement>) => providentManagement.body)
            );
        }
        return of(new ProvidentManagement());
    }
}

export const providentManagementRoute: Routes = [
    {
        path: '',
        component: ProvidentManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProvidentManagementDetailComponent,
        resolve: {
            providentManagement: ProvidentManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProvidentManagementUpdateComponent,
        resolve: {
            providentManagement: ProvidentManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProvidentManagementUpdateComponent,
        resolve: {
            providentManagement: ProvidentManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const providentManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProvidentManagementDeletePopupComponent,
        resolve: {
            providentManagement: ProvidentManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
