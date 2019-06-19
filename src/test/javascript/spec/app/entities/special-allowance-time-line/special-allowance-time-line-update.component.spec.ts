/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SpecialAllowanceTimeLineUpdateComponent } from 'app/entities/special-allowance-time-line/special-allowance-time-line-update.component';
import { SpecialAllowanceTimeLineService } from 'app/entities/special-allowance-time-line/special-allowance-time-line.service';
import { SpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

describe('Component Tests', () => {
    describe('SpecialAllowanceTimeLine Management Update Component', () => {
        let comp: SpecialAllowanceTimeLineUpdateComponent;
        let fixture: ComponentFixture<SpecialAllowanceTimeLineUpdateComponent>;
        let service: SpecialAllowanceTimeLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SpecialAllowanceTimeLineUpdateComponent]
            })
                .overrideTemplate(SpecialAllowanceTimeLineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpecialAllowanceTimeLineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialAllowanceTimeLineService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SpecialAllowanceTimeLine(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.specialAllowanceTimeLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SpecialAllowanceTimeLine();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.specialAllowanceTimeLine = entity;
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
