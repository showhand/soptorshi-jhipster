import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Designation } from 'app/shared/model/designation.model';
import { DesignationService } from './designation.service';
import { DesignationComponent } from './designation.component';
import { DesignationDetailComponent } from './designation-detail.component';
import { DesignationUpdateComponent } from './designation-update.component';
import { DesignationDeletePopupComponent } from './designation-delete-dialog.component';
import { IDesignation } from 'app/shared/model/designation.model';

@Injectable({ providedIn: 'root' })
export class DesignationResolve implements Resolve<IDesignation> {
    constructor(private service: DesignationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDesignation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Designation>) => response.ok),
                map((designation: HttpResponse<Designation>) => designation.body)
            );
        }
        return of(new Designation());
    }
}

export const designationRoute: Routes = [
    {
        path: '',
        component: DesignationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DesignationDetailComponent,
        resolve: {
            designation: DesignationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DesignationUpdateComponent,
        resolve: {
            designation: DesignationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DesignationUpdateComponent,
        resolve: {
            designation: DesignationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DesignationDeletePopupComponent,
        resolve: {
            designation: DesignationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
