/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplySalesRepresentativeUpdateComponent } from 'app/entities/supply-sales-representative/supply-sales-representative-update.component';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative/supply-sales-representative.service';
import { SupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';

describe('Component Tests', () => {
    describe('SupplySalesRepresentative Management Update Component', () => {
        let comp: SupplySalesRepresentativeUpdateComponent;
        let fixture: ComponentFixture<SupplySalesRepresentativeUpdateComponent>;
        let service: SupplySalesRepresentativeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplySalesRepresentativeUpdateComponent]
            })
                .overrideTemplate(SupplySalesRepresentativeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplySalesRepresentativeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplySalesRepresentativeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplySalesRepresentative(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplySalesRepresentative = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplySalesRepresentative();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplySalesRepresentative = entity;
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
