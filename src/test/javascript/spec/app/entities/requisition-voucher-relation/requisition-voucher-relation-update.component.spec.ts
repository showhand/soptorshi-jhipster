/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionVoucherRelationUpdateComponent } from 'app/entities/requisition-voucher-relation/requisition-voucher-relation-update.component';
import { RequisitionVoucherRelationService } from 'app/entities/requisition-voucher-relation/requisition-voucher-relation.service';
import { RequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

describe('Component Tests', () => {
    describe('RequisitionVoucherRelation Management Update Component', () => {
        let comp: RequisitionVoucherRelationUpdateComponent;
        let fixture: ComponentFixture<RequisitionVoucherRelationUpdateComponent>;
        let service: RequisitionVoucherRelationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionVoucherRelationUpdateComponent]
            })
                .overrideTemplate(RequisitionVoucherRelationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RequisitionVoucherRelationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionVoucherRelationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionVoucherRelation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionVoucherRelation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionVoucherRelation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionVoucherRelation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
