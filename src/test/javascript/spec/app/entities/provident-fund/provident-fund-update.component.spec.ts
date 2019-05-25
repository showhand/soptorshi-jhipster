/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentFundUpdateComponent } from 'app/entities/provident-fund/provident-fund-update.component';
import { ProvidentFundService } from 'app/entities/provident-fund/provident-fund.service';
import { ProvidentFund } from 'app/shared/model/provident-fund.model';

describe('Component Tests', () => {
    describe('ProvidentFund Management Update Component', () => {
        let comp: ProvidentFundUpdateComponent;
        let fixture: ComponentFixture<ProvidentFundUpdateComponent>;
        let service: ProvidentFundService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentFundUpdateComponent]
            })
                .overrideTemplate(ProvidentFundUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProvidentFundUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvidentFundService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProvidentFund(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.providentFund = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProvidentFund();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.providentFund = entity;
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
