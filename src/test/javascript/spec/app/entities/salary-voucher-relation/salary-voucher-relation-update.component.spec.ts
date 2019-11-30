/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SalaryVoucherRelationUpdateComponent } from 'app/entities/salary-voucher-relation/salary-voucher-relation-update.component';
import { SalaryVoucherRelationService } from 'app/entities/salary-voucher-relation/salary-voucher-relation.service';
import { SalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';

describe('Component Tests', () => {
    describe('SalaryVoucherRelation Management Update Component', () => {
        let comp: SalaryVoucherRelationUpdateComponent;
        let fixture: ComponentFixture<SalaryVoucherRelationUpdateComponent>;
        let service: SalaryVoucherRelationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SalaryVoucherRelationUpdateComponent]
            })
                .overrideTemplate(SalaryVoucherRelationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalaryVoucherRelationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalaryVoucherRelationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalaryVoucherRelation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salaryVoucherRelation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalaryVoucherRelation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salaryVoucherRelation = entity;
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
