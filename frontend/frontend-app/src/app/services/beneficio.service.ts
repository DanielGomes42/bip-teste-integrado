// src/app/services/beneficio.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio } from '../beneficio';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private api = 'http://localhost:8080/api/beneficios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.api);
  }

  buscar(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/${id}`);
  }

  criar(b: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.api, b);
  }

  atualizar(id: number, b: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.api}/${id}`, b);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
